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

describe('LevelFour e2e test', () => {
  const levelFourPageUrl = '/level-four';
  const levelFourPageUrlPattern = new RegExp('/level-four(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const levelFourSample = {
    identifier: 'radical deposit drive',
    criticalSuccessFactors: 'workforce orchid',
    populationSize: 'transmitting override',
    statisticalError: 'Visionary',
    confidenceLevel: 'cross-platform Manager',
    requiredSampleSize: 'Wooden indexing',
    estimatedResponseRate: 'cultivate',
    surveyParticipantsNumber: 54578,
  };

  let levelFour;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/level-fours+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/level-fours').as('postEntityRequest');
    cy.intercept('DELETE', '/api/level-fours/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (levelFour) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/level-fours/${levelFour.id}`,
      }).then(() => {
        levelFour = undefined;
      });
    }
  });

  it('LevelFours menu should load LevelFours page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('level-four');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('LevelFour').should('exist');
    cy.url().should('match', levelFourPageUrlPattern);
  });

  describe('LevelFour page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(levelFourPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create LevelFour page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/level-four/new$'));
        cy.getEntityCreateUpdateHeading('LevelFour');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelFourPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/level-fours',
          body: levelFourSample,
        }).then(({ body }) => {
          levelFour = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/level-fours+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/level-fours?page=0&size=20>; rel="last",<http://localhost/api/level-fours?page=0&size=20>; rel="first"',
              },
              body: [levelFour],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(levelFourPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details LevelFour page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('levelFour');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelFourPageUrlPattern);
      });

      it('edit button click should load edit LevelFour page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('LevelFour');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelFourPageUrlPattern);
      });

      it('edit button click should load edit LevelFour page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('LevelFour');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelFourPageUrlPattern);
      });

      it('last delete button click should delete instance of LevelFour', () => {
        cy.intercept('GET', '/api/level-fours/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('levelFour').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', levelFourPageUrlPattern);

        levelFour = undefined;
      });
    });
  });

  describe('new LevelFour page', () => {
    beforeEach(() => {
      cy.visit(`${levelFourPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('LevelFour');
    });

    it('should create an instance of LevelFour', () => {
      cy.get(`[data-cy="identifier"]`).type('Computer Account').should('have.value', 'Computer Account');

      cy.get(`[data-cy="criticalSuccessFactors"]`).type('Tala input deploy').should('have.value', 'Tala input deploy');

      cy.get(`[data-cy="populationSize"]`).type('Soap').should('have.value', 'Soap');

      cy.get(`[data-cy="statisticalError"]`).type('Gorgeous enable').should('have.value', 'Gorgeous enable');

      cy.get(`[data-cy="confidenceLevel"]`).type('payment systemic').should('have.value', 'payment systemic');

      cy.get(`[data-cy="requiredSampleSize"]`).type('National Account').should('have.value', 'National Account');

      cy.get(`[data-cy="estimatedResponseRate"]`).type('Rubber Granite').should('have.value', 'Rubber Granite');

      cy.get(`[data-cy="surveyParticipantsNumber"]`).type('79818').should('have.value', '79818');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        levelFour = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', levelFourPageUrlPattern);
    });
  });
});
