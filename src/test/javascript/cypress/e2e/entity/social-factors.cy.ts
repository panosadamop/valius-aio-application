import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('SocialFactors e2e test', () => {
  const socialFactorsPageUrl = '/social-factors';
  const socialFactorsPageUrlPattern = new RegExp('/social-factors(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const socialFactorsSample = { value: 'Analyst', language: 'GREEK' };

  let socialFactors;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/social-factors+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/social-factors').as('postEntityRequest');
    cy.intercept('DELETE', '/api/social-factors/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (socialFactors) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/social-factors/${socialFactors.id}`,
      }).then(() => {
        socialFactors = undefined;
      });
    }
  });

  it('SocialFactors menu should load SocialFactors page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('social-factors');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SocialFactors').should('exist');
    cy.url().should('match', socialFactorsPageUrlPattern);
  });

  describe('SocialFactors page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(socialFactorsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SocialFactors page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/social-factors/new$'));
        cy.getEntityCreateUpdateHeading('SocialFactors');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', socialFactorsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/social-factors',
          body: socialFactorsSample,
        }).then(({ body }) => {
          socialFactors = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/social-factors+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/social-factors?page=0&size=20>; rel="last",<http://localhost/api/social-factors?page=0&size=20>; rel="first"',
              },
              body: [socialFactors],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(socialFactorsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SocialFactors page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('socialFactors');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', socialFactorsPageUrlPattern);
      });

      it('edit button click should load edit SocialFactors page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SocialFactors');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', socialFactorsPageUrlPattern);
      });

      it('edit button click should load edit SocialFactors page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SocialFactors');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', socialFactorsPageUrlPattern);
      });

      it('last delete button click should delete instance of SocialFactors', () => {
        cy.intercept('GET', '/api/social-factors/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('socialFactors').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', socialFactorsPageUrlPattern);

        socialFactors = undefined;
      });
    });
  });

  describe('new SocialFactors page', () => {
    beforeEach(() => {
      cy.visit(`${socialFactorsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SocialFactors');
    });

    it('should create an instance of SocialFactors', () => {
      cy.get(`[data-cy="value"]`).type('Louisiana salmon').should('have.value', 'Louisiana salmon');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        socialFactors = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', socialFactorsPageUrlPattern);
    });
  });
});
