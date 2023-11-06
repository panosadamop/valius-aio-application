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

describe('NetPromoterScore e2e test', () => {
  const netPromoterScorePageUrl = '/net-promoter-score';
  const netPromoterScorePageUrlPattern = new RegExp('/net-promoter-score(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const netPromoterScoreSample = { value: 'Borders IB implementation', language: 'ENGLISH' };

  let netPromoterScore;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/net-promoter-scores+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/net-promoter-scores').as('postEntityRequest');
    cy.intercept('DELETE', '/api/net-promoter-scores/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (netPromoterScore) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/net-promoter-scores/${netPromoterScore.id}`,
      }).then(() => {
        netPromoterScore = undefined;
      });
    }
  });

  it('NetPromoterScores menu should load NetPromoterScores page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('net-promoter-score');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('NetPromoterScore').should('exist');
    cy.url().should('match', netPromoterScorePageUrlPattern);
  });

  describe('NetPromoterScore page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(netPromoterScorePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create NetPromoterScore page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/net-promoter-score/new$'));
        cy.getEntityCreateUpdateHeading('NetPromoterScore');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', netPromoterScorePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/net-promoter-scores',
          body: netPromoterScoreSample,
        }).then(({ body }) => {
          netPromoterScore = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/net-promoter-scores+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/net-promoter-scores?page=0&size=20>; rel="last",<http://localhost/api/net-promoter-scores?page=0&size=20>; rel="first"',
              },
              body: [netPromoterScore],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(netPromoterScorePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details NetPromoterScore page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('netPromoterScore');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', netPromoterScorePageUrlPattern);
      });

      it('edit button click should load edit NetPromoterScore page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('NetPromoterScore');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', netPromoterScorePageUrlPattern);
      });

      it('edit button click should load edit NetPromoterScore page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('NetPromoterScore');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', netPromoterScorePageUrlPattern);
      });

      it('last delete button click should delete instance of NetPromoterScore', () => {
        cy.intercept('GET', '/api/net-promoter-scores/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('netPromoterScore').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', netPromoterScorePageUrlPattern);

        netPromoterScore = undefined;
      });
    });
  });

  describe('new NetPromoterScore page', () => {
    beforeEach(() => {
      cy.visit(`${netPromoterScorePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('NetPromoterScore');
    });

    it('should create an instance of NetPromoterScore', () => {
      cy.get(`[data-cy="value"]`).type('Planner multi-byte').should('have.value', 'Planner multi-byte');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        netPromoterScore = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', netPromoterScorePageUrlPattern);
    });
  });
});
